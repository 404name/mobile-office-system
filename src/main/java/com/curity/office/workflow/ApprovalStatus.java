package com.curity.office.workflow;


/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 20:25
 **/
public enum ApprovalStatus implements State {
    draft(0, "草稿"){
        @Override
        public void doWork(StateMachine context) {
            //先提取业务对象
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{草稿 } -> {部门审核}
             * 不通过：{草稿 } -> {草稿}
             */
            if(context.getPass()){
                context.setState(department_check);
            }else{
                context.setState(draft);
            }
        }
    },
    department_check(1, "部门审核"){
        @Override
        public void doWork(StateMachine context) {
//先提取业务对象
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{部门审核 } -> {办公室秘书初审}
             * 不通过：{部门审核 } -> {草稿}
             */
            if(context.getPass()){
                context.setState(first_trial);
            }else{
                context.setState(draft);
            }
        }
    },
    first_trial(2, "办公室秘书初审"){
        @Override
        public void doWork(StateMachine context) {
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{办公室秘书初审 } -> {办公室主任审核}
             * 不通过：{办公室秘书初审 } -> {部门审核}
             */
            if(context.getPass()){
                context.setState(recheck);
            }else{
                context.setState(department_check);
            }
        }
    },
    recheck(3, "办公室主任审核"){
        @Override
        public void doWork(StateMachine context) {
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{办公室主任审核 } -> {公司领导审核}
             * 不通过：{办公室主任审核 } -> {部门审核}
             */
            if(context.getPass()){
                context.setState(leader_recheck);
            }else{
                context.setState(department_check);
            }
        }
    },
    leader_recheck(4, "公司领导审核"){
        @Override
        public void doWork(StateMachine context) {
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{公司领导审核 } -> {秘书校对发行}
             * 不通过：{公司领导审核 } -> {部门审核}
             */
            if(context.getPass()){
                context.setState(proofread);
            }else{
                context.setState(department_check);
            }
        }
    },
    proofread(5, "秘书校对发行"){
        @Override
        public void doWork(StateMachine context) {
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{秘书校对发行 } -> {发行成功}
             * 不通过：{秘书校对发行 } -> {部门审核}
             */
            if(context.getPass()){
                context.setState(publish);
            }else{
                context.setState(department_check);
            }
        }
    },
    publish(6, "发行成功"){
        @Override
        public void doWork(StateMachine context) {
            ApprovalStatus state = (ApprovalStatus) context.getState();
            /**
             * 通过：{发行成功 } -> {发行成功}
             * 不通过：{发行成功 } -> {秘书校对发行}
             */
            if(context.getPass()){
                context.setState(publish);
            }else{
                context.setState(proofread);
            }
        }
    };

    private Integer state;
    private String name;

    public int getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStateCode() {
        return Integer.toString(this.ordinal());
    }
    ApprovalStatus(Integer state, String name) {
        this.state = state;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ApprovalStatus{" +
                "state=" + state +
                ", name='" + name + '\'' +
                '}';
    }
}
