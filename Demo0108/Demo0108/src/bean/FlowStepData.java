package bean;

public class FlowStepData extends ShowTextParent{
	private String des;
	
	public FlowStepData(String des) {
		super();
		this.des = des;
	}
	@Override
	public String getShowText() {
		return des;
	}
}
