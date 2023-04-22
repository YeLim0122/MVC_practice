-- board.sql
drop table board;

CREATE TABLE board(
	num NUMBER(8) PRIMARY KEY, -- 글번호
	userid VARCHAR2(30) NOT NULL, --작성자 아이디
	subject VARCHAR2(200),	-- 제목
	content VARCHAR2(2000),	-- 글 내용
	wdate TIMESTAMP DEFAULT systimestamp,	-- 작성일
	readnum NUMBER(8) DEFAULT 0,	-- 조회수
	filename VARCHAR2(500),		-- 첨부파일 명
	filesize NUMBER(8)		-- 첨부파일 크기
);

DROP sequence board_seq;

CREATE sequence board_seq
start WITH 1 increment by 1 nocache;