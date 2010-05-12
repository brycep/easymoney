package net.switchcase.easymoney.client.common;

import java.io.Serializable;

/** In order to be used inside of the DataTable component, 
 *  the model should implement this interface. 
 *  
 * @author bryce
 *
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */
public interface ModelObject extends Serializable {
	
	Long getId();

}
