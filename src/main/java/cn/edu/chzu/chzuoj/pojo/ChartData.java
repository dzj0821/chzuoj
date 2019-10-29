package cn.edu.chzu.chzuoj.pojo;

public class ChartData {
	private String timestamp;
	private String count;

	@Override
	public String toString() {
		return "ChartData [timestamp=" + timestamp + ", count=" + count + "]";
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
