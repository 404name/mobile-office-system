package com.curity.office.workflow;

/**
 * 状态接口
 */
public interface State {
    /**
     * 执行状态对应的处理功能
     *
     * @param context 上下文实例
     */
    void doWork(StateMachine context);
}

