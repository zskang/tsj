package cn.promore.bf.serivce;


public interface EntitySequenceService {
	public String findNext(String name,String type);
	public void resetEntitySequence(String type);
}
