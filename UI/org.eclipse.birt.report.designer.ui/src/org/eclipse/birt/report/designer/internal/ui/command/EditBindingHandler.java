/**
 * 
 */

package org.eclipse.birt.report.designer.internal.ui.command;

import org.eclipse.birt.report.designer.core.model.SessionHandleAdapter;
import org.eclipse.birt.report.designer.internal.ui.editors.schematic.editparts.ListEditPart;
import org.eclipse.birt.report.designer.internal.ui.editors.schematic.editparts.ReportElementEditPart;
import org.eclipse.birt.report.designer.internal.ui.editors.schematic.editparts.TableEditPart;
import org.eclipse.birt.report.designer.nls.Messages;
import org.eclipse.birt.report.designer.ui.dialogs.DataBindingDialog;
import org.eclipse.birt.report.model.api.CommandStack;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 *
 */
public class EditBindingHandler extends SelectionHandler
{

	public Object execute( ExecutionEvent event ) throws ExecutionException
	{
		super.execute( event );

		// Get the first item in the list and pass the model object to the
		// dialog
		TableEditPart editPart = getTableEditPart( );

		ListEditPart listPart = getListEditPart( );
		
		ReportElementEditPart part = getTableMultipleEditPart( );

		if ( editPart != null || listPart != null  || part != null)
		{
			CommandStack stack = SessionHandleAdapter.getInstance( )
					.getCommandStack( );

			stack.startTrans( Messages.getString( "DesignerActionBarContributor.menu.element.editDataBinding" ) ); //$NON-NLS-1$
			DataBindingDialog dialog = new DataBindingDialog( PlatformUI.getWorkbench( )
					.getDisplay( )
					.getActiveShell( ),
					getSelectedElement( ) );

			if ( dialog.open( ) == Dialog.OK )
			{
				stack.commit( );
			}
			else
			{
				stack.rollback( );
			}
		}

		return new Boolean( true );
	}

	private ReportItemHandle getSelectedElement( )
	{
		if ( getTableEditPart( ) != null
				&& getTableEditPart( ).getModel( ) instanceof ReportItemHandle )
		{
			return (ReportItemHandle) getTableEditPart( ).getModel( );

		}
		
		if ( getTableMultipleEditPart( ) != null
				&& getTableMultipleEditPart( ).getModel( ) instanceof ReportItemHandle )
		{
			return (ReportItemHandle) getTableMultipleEditPart( ).getModel( );

		}

		if ( getListEditPart( ) != null
				&& getListEditPart( ).getModel( ) instanceof ReportItemHandle )
		{
			return (ReportItemHandle) getListEditPart( ).getModel( );

		}

		return null;
	}
}
