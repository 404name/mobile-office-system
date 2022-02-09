package com.curity.office.workflow;

/**
 * 状态处理机，相当于状态模式中的Context(上下文)
 */
public class StateMachine {
    /**
     * 持有一个状态对象
     */
    private State state;

    /**
     * 流程处理需要的业务数据对象
     */
    private Boolean pass;

    public void doWork(){
        //转调相应的状态对象真正完成功能处理
        this.state.doWork(this);
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }



    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public StateMachine(State state, Boolean pass) {
        this.state = state;
        this.pass = pass;
    }
}

