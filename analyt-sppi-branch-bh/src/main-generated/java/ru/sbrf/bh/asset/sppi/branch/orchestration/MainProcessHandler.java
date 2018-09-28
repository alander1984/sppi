package ru.sbrf.bh.asset.sppi.branch.orchestration;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.sbrf.bh.asset.sppi.branch.dto.pl.startform.ShowStartFormRequestDto;
import ru.sbrf.bh.asset.sppi.branch.dto.pl.startform.ShowStartFormResponseDto;
import ru.sbrf.ufs.platform.workflow.EventContext;
import ru.sbrf.ufs.platform.workflow.HistoryStep;
import ru.sbrf.ufs.platform.workflow.HistoryStep.StepStatus;
import ru.sbrf.ufs.platform.workflow.Transition;

/**
 * Platform workflowfor process: Главный процесс
 * 
 * @author
 */
public class MainProcessHandler {

	/**
	* 
	*/
	@Autowired
	@Qualifier("workflowExecutor")
	private transient EventContext context;

	/**
	 * Initialization of logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MainProcessHandler.class);

	/**
	 * Flow state process method. Статическая форма: &quot;StartForm&quot;
	 */
	public Transition startFormInit() {
		LOGGER.info("{} Initializing step StartForm flow MainProcess", context.getProcessId());
		try {
			MainProcess o = context.getFlowAttr("o"); // Gets flow context 
			boolean start = o == null;
			if (o == null) {
				o = new MainProcess(); // Initiates flow context if it not created earlier
			}
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(o); // Initiates related services
			if (start) {
				o.begin();
				context.setFlowAttr("o", o); // Saves flow context 
			}
			updateBreadcrumbs(o);
			ShowStartFormResponseDto showstartformresponsedto = o.startForm(null); // Статическая форма: "StartForm"
			context.setOutput(showstartformresponsedto); // Sets state output data  
			context.setFlowAttr("o", o); // Saves flow context 
			updateBreadcrumbs(o);
			setAllBreadcrumbsVisible();
			return Transition.stop();
		} catch (RuntimeException e) {
			LOGGER.error("Error initializing step StartForm flow MainProcess", e);
			throw new MainProcessException(MainProcessException.ASBO_MAINPROCESS_STARTFORM_ERR,
					"Error initializing step StartForm flow MainProcess", e);
		}
	}

	/**
	 * Flow state process method. Статическая форма: &quot;StartForm&quot;
	 */
	public Transition startForm() {
		LOGGER.info("{} Executing step startForm flow MainProcess", context.getProcessId());
		try {
			MainProcess o = context.getFlowAttr("o"); // Gets flow context 
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(o); // Initiates related services
			ShowStartFormRequestDto showstartformrequestdto = context.parseInputToJson(ShowStartFormRequestDto.class); // Gets state input data 
			o.startForm(showstartformrequestdto); // Статическая форма: "StartForm"
			context.setFlowAttr("o", o); // Saves flow context 
			setAllBreadcrumbsVisible();
			o.end();
			return Transition.go(MainProcessConstants.TO_END);

		} catch (MainProcessException e) {
			throw e;
		} catch (RuntimeException e) {
			LOGGER.error("Error executing step StartForm flow MainProcess", e);
			throw new MainProcessException(MainProcessException.ASBO_MAINPROCESS_STARTFORM_ERR,
					"Error executing step StartForm flow MainProcess", e);
		}
	}

	/**
	 * Устанавливает видимость хлебных крошек шагов при входе в подпроцесс
	 */
	private void setAllBreadcrumbsVisible() {
		for (HistoryStep hs : context.getHistory()) {
			if (hs.getFlowId() != Integer.MAX_VALUE) {
				hs.setFlowId(context.getCurrentStep().getFlowId());
			}
		}
	}

	/**
	 * Метод скрывает шаги подпроцесса и переписывает flow id шагов основного
	 * процесса
	 */
	private void restoreOriginalFlowIdForHistorySteps() {
		disableUnreachableSteps();
		setAllBreadcrumbsVisible();
	}

	/**
	 * Метод устанавливает статус HIDDEN для всех шагов процесса имя которых не
	 * совпадает с именем текущего процесса
	 */
	private void disableUnreachableSteps() {
		String currentFlowName = context.getCurrentStep().getFlowName();
		List<HistoryStep> history = Lists.newArrayList(context.getHistory());
		for (HistoryStep step : history.subList(1, history.size())) {
			if (!Objects.equals(context.getCurrentStep().getFlowId(), step.getFlowId())
					&& !currentFlowName.equals(step.getFlowName())) {
				step.setStatus(StepStatus.HIDDEN);
				step.setFlowId(Integer.MAX_VALUE);
			} else {
				break;
			}
		}
	}

	/**
	 * Метод возвращает статус шага процесса на основе вычисляемых параметров
	 * видимости и доступности хлебной крошки
	 */
	private StepStatus getStepStatus(boolean visible, boolean enabled) {
		if (visible && enabled) {
			return StepStatus.ACTIVE;
		} else if (visible && !enabled) {
			return StepStatus.DISABLED;
		} else if (!visible && enabled) {
			return StepStatus.HIDDEN;
		} else {
			return null;
		}
	}

	/**
	 * Устанавливает параметры отображения хлебных крошек шага
	 * &quot;StartForm&quot; на основе результата выполнения OCL выражений
	 */
	public StepStatus getStartFormBreadcrumbStatus(MainProcess process) {
		return getStepStatus(true, true);
	}

	/**
	 * Устанавливает параметры отображения хлебных крошек для всех шагов
	 */
	public void updateBreadcrumbs(MainProcess process) {
		setBreadcrumbStatus(MainProcessConstants.STARTFORM, getStartFormBreadcrumbStatus(process));
	}

	/**
	 * Устанавливает параметры отображения хлебной крошки на основе результата
	 * выполнения OCL условий видимости и доступности шагов в процессе
	 */
	private void setBreadcrumbStatus(String stateName, StepStatus status) {
		List<HistoryStep> history = Lists.newArrayList(context.getHistory());
		if (history.size() > 1) {
			for (HistoryStep hs : history.subList(1, history.size())) {
				if (hs.getStateName().equals(stateName)) {
					hs.setStatus(status);
				}
			}
		}
	}

}