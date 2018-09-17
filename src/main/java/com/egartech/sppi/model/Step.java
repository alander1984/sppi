package com.egartech.sppi.model;

public class Step {

    private Question current;
    private Question next=null;
    private String answer;
    private Long processId;

    public Step(Question current, String answer){
        this.current = current;
        this.answer = answer;
    }

    public Step(Question firstQuestion, Long processId) {
        this.current = firstQuestion;
        this.processId = processId;
    }

    public Question getCurrent() {
        return current;
    }

    public void setCurrent(Question current) {
        this.current = current;
    }

    public Question getNext() {
        return next;
    }

    public void setNext(Question next) {
        this.next = next;
    }

    public void setNextQ(String qCode) {
        this.next = new Question();
        this.next.setCode(qCode);
    }

    public void testFailed() {
        this.next = new Question();
        this.next.setCode("FAIL");
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }
}
