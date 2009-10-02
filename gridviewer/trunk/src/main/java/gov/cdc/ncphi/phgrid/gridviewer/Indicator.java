package gov.cdc.ncphi.phgrid.gridviewer;

import java.io.Serializable;

public class Indicator implements Serializable {

	public String getClassifier() {
		return classifier;
	}
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String classifier;
	private String name;
	public int compare (Indicator o1, Indicator o2)
	{
		int returnable = o1.classifier.compareTo(o2.classifier);
		if (returnable == 0)
		{
			returnable = o1.name.compareTo(o2.name);
		}
		return returnable;
	}
}
