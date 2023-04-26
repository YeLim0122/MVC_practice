<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String myctx = request.getContextPath();
%>
        </article>
        <!-- aside: 사이드영역, 사이드 메뉴 -->
        <aside>
            <!-- aside -->
            <nav>
                <ul>
                    <li><a href="<%= myctx %>/uploadForm.do">파일 업로드</a></li>
                    <li><a href="<%= myctx %>/user/myPage.do">MyPage</a></li>
                </ul>
            </nav>
        </aside>
        <div class="cls"></div>
        <!-- footer: 푸터영역, 회사 주소 연락처 copyright -->
        <footer>
            <!-- footer -->
            Copyright | 회사소개 | 연락처 | 주소
        </footer>
    </div> <!-- wrap 끝 -->
    
</body>
</html>