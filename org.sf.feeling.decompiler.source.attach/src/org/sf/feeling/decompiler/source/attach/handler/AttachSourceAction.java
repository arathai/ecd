/*******************************************************************************
 * Copyright (c) 2017 Chen Chao(cnfree2000@hotmail.com).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Chen Chao  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.decompiler.source.attach.handler;

import java.util.List;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.action.Action;
import org.sf.feeling.decompiler.JavaDecompilerPlugin;
import org.sf.feeling.decompiler.source.attach.i18n.Messages;

@SuppressWarnings("rawtypes")
public class AttachSourceAction extends Action
{

	private List selection = null;

	public AttachSourceAction( List selection )
	{
		super( Messages.getString( "AttachSourceAction.Action.Name" ) ); //$NON-NLS-1$
		this.selection = selection;
	}

	@Override
	public void run( )
	{
		if ( JavaDecompilerPlugin.getDefault( ).isEnableExtension( ) )
		{
			if ( selection == null || selection.isEmpty( ) )
				return;

			Object firstElement = selection.get( 0 );
			if ( selection.size( ) == 1 && firstElement instanceof IClassFile )
			{
				IClassFile classFile = (IClassFile) firstElement;
				IPackageFragmentRoot root = (IPackageFragmentRoot) classFile.getParent( ).getParent( );
				JavaDecompilerPlugin.getDefault( ).attachSource( root, true );
			}
			else if ( selection.size( ) == 1 && firstElement instanceof IPackageFragmentRoot )
			{
				IPackageFragmentRoot root = (IPackageFragmentRoot) firstElement;
				JavaDecompilerPlugin.getDefault( ).attachSource( root, true );
			}
			else
			{
				IPackageFragmentRoot root = null;
				if ( firstElement instanceof IClassFile )
				{
					root = (IPackageFragmentRoot) ( (IClassFile) firstElement ).getParent( ).getParent( );
				}
				else if ( firstElement instanceof IPackageFragment )
				{
					root = (IPackageFragmentRoot) ( (IPackageFragment) firstElement ).getParent( );
				}
				if ( root == null )
					return;
				JavaDecompilerPlugin.getDefault( ).attachSource( root, true );
			}
		}
	}

	@Override
	public boolean isEnabled( )
	{
		return JavaDecompilerPlugin.getDefault( ).isEnableExtension( ) && selection != null;
	}

}
