package cz.gattserver.web.common.ui;

/**
 * Udává v jakém stavu se nachází stránka, kterou jsem právě vystavěl - je v
 * pořádku nebo na ní došlo k chybám ?
 * 
 * @author Gattaka
 * 
 */
public enum PageState {

	CLEAN, E404, E403, E500

}
