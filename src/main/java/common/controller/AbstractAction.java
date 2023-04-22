package common.controller;

abstract public class AbstractAction implements Action {
	
	// execute() 추상메소드를 가짐
	private String viewPage;	// 보여줄 뷰 페이지 이름
	private boolean isRedirect;	// true이면 redirect 이동, false면 forward 이동
	
	
	// setter, getter
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}// -----------------------------
