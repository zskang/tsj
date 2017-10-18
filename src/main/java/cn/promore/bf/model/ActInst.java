package cn.promore.bf.model;

public class ActInst {
	private String id_;
	private String proc_def_id_;
	private String proc_inst_id_;
	private String execution_id_;
	private String act_id_;
	private String task_id_;
	private String act_name_;
	private String act_type_;
	private String assignee_;
	
	private String assingneeName_;
	
	private String start_time_;
	private String end_time_;

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getProc_def_id_() {
		return proc_def_id_;
	}

	public void setProc_def_id_(String proc_def_id_) {
		this.proc_def_id_ = proc_def_id_;
	}

	public String getProc_inst_id_() {
		return proc_inst_id_;
	}

	public void setProc_inst_id_(String proc_inst_id_) {
		this.proc_inst_id_ = proc_inst_id_;
	}

	public String getExecution_id_() {
		return execution_id_;
	}

	public void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}

	public String getAct_id_() {
		return act_id_;
	}

	public void setAct_id_(String act_id_) {
		this.act_id_ = act_id_;
	}

	public String getTask_id_() {
		return task_id_;
	}

	public void setTask_id_(String task_id_) {
		this.task_id_ = task_id_;
	}

	public String getAct_name_() {
		return act_name_;
	}

	public void setAct_name_(String act_name_) {
		this.act_name_ = act_name_;
	}

	public String getAct_type_() {
		return act_type_;
	}

	public void setAct_type_(String act_type_) {
		this.act_type_ = act_type_;
	}

	public String getAssignee_() {
		return assignee_;
	}

	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}

	public String getStart_time_() {
		return start_time_;
	}

	public void setStart_time_(String start_time_) {
		this.start_time_ = start_time_;
	}

	public String getEnd_time_() {
		return end_time_;
	}

	public void setEnd_time_(String end_time_) {
		this.end_time_ = end_time_;
	}

	@Override
	public String toString() {
		return "ActInst [id_=" + id_ + ", proc_def_id_=" + proc_def_id_ + ", proc_inst_id_=" + proc_inst_id_
				+ ", execution_id_=" + execution_id_ + ", act_id_=" + act_id_ + ", task_id_=" + task_id_
				+ ", act_name_=" + act_name_ + ", act_type_=" + act_type_ + ", assignee_=" + assignee_
				+ ", start_time_=" + start_time_ + ", end_time_=" + end_time_ + "]";
	}

	public String getAssingneeName_() {
		return assingneeName_;
	}

	public void setAssingneeName_(String assingneeName_) {
		this.assingneeName_ = assingneeName_;
	}

}
