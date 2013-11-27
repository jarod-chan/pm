<%@ page language="java" pageEncoding="UTF-8"%>

<style type="text/css">

	/*
	 *	Rules below are for simulated drop-down/pop-up lists
	 */

	ul {
		/* rules common to BOTH inner and outer UL */
		z-index:	100000;
		margin:		1ex 0;
		padding:	0;
		list-style:	none;
		cursor:		pointer;
		border:		1px solid Black;
		/* rules for outer UL only */
		width:		12ex;
		position:	relative;
		margin-left: 5px;
	}
	ul li {
		background-color: #EEE;
		padding: 0.15em 1em 0.3em 5px;
	}
	ul ul {
		display:	none;
		position:	absolute;
		width:		100%;
		left:		-1px;
		/* Pop-Up */
		bottom:		0;
		margin:		0;
		margin-bottom: 1.55em;
	}
	.ui-layout-north ul ul {
		/* Drop-Down */
		bottom:		auto;
		margin:		0;
		margin-top:	1.45em;
	}
	ul ul li		{ padding: 3px 1em 3px 5px; }
	ul ul li:hover	{ background-color: #1E8EFF; }
	ul li:hover ul	{ display:	block; background-color: #EEE; }
	
	
 	 li .no_show{color: #000; width:100%; display:block; text-decoration: none;} 
	 li .no_show:hover{color: #FFFFFF;}
	</style>
	


