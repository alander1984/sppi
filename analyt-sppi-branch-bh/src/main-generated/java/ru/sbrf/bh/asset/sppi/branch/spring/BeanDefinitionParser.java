package ru.sbrf.bh.asset.sppi.branch.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import ru.sbrf.ufs.platform.core.spring.SpringContextLoader;
import ru.sbrf.ufs.platform.core.spring.context.SimpleTemplateBeanDefinitionParser;

public class BeanDefinitionParser extends SimpleTemplateBeanDefinitionParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanDefinitionParser.class);
	private static final String CHANNEL_ATTR_NAME = "channel";
	private static final String SUBSYSTEM_ATTR_NAME = "subsystem";

	public BeanDefinitionParser(String template) {
		super(template);
	}

	@Override
	protected void processBody(Element element, Map<String, Object> data) {
		try (final InputStream envProps = BeanDefinitionParser.class.getClassLoader()
				.getResourceAsStream(SpringContextLoader.ENV_PROP_FILE)) {
			if (envProps != null) {
				Properties properties = new Properties();
				properties.load(envProps);
				data.put(CHANNEL_ATTR_NAME, properties.getProperty(CHANNEL_ATTR_NAME));
				data.put(SUBSYSTEM_ATTR_NAME, properties.getProperty(SUBSYSTEM_ATTR_NAME));
			} else {
				LOGGER.info("Can't load {} file", SpringContextLoader.ENV_PROP_FILE);
			}
		} catch (IOException e) {
			LOGGER.info("Can't load {} file", SpringContextLoader.ENV_PROP_FILE);
			LOGGER.error("", e);
		}
	}
}