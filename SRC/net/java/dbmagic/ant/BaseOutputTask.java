/*  -----------------
 *  SysVision DBMagic
 *  -----------------
 *
 *  Copyright 2006-2012 SysVision - Consultadoria e Desenvolvimento em Sistemas de Informática, Lda.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.java.dbmagic.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public abstract class BaseOutputTask extends Task implements OutputTask {
	private Container container = null;
	
	public BaseOutputTask() {
		super();
	}
	
    public void setContainer(Container container) {
		this.container = container;
	}

	public abstract void execute() throws BuildException;
	
	public Container getContainer() {
		return container;
	}
}
