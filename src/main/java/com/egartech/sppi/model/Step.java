package com.egartech.sppi.model;

public class Step {

    private Question current;
    private Question next=null;
    private String answer;
    private int i;

    public Step(Question current, String answer){
        this.current = current;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
