package net.switchcase.easymoney.client.common;

/** This class represents a row of data in a DataTable.
 * 
 * @author bryce
 *
 */
public class DataRow {
	private ModelAdapter modelAdapter;
	private ModelObject dataObject;
	
	public DataRow(ModelAdapter modelAdapter, 
				   ModelObject dataObject)  {
		this.modelAdapter = modelAdapter;
		this.dataObject = dataObject;
	}
	
	public ModelAdapter getModelAdapter() {
		return modelAdapter;
	}
	
	public ModelObject getDataObject()  {
		return dataObject;
	}
}
