package cn.edu.chzu.chzuoj.pojo;

/**
 * 
 * @author dzj0821
 *
 */
public class Permission {
	private Boolean administrator;
	private Boolean contestCreator;
	private Boolean problemEditor;
	private Boolean classManager;

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	public Boolean getContestCreator() {
		return contestCreator;
	}

	public void setContestCreator(Boolean contestCreator) {
		this.contestCreator = contestCreator;
	}

	public Boolean getProblemEditor() {
		return problemEditor;
	}

	public void setProblemEditor(Boolean problemEditor) {
		this.problemEditor = problemEditor;
	}

	public Boolean getClassManager() {
		return classManager;
	}

	public void setClassManager(Boolean classManager) {
		this.classManager = classManager;
	}
}
