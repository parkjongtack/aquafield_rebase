package com.soft.web.util;

public class PageUtil {
	private int pageNum;			//-- 현 페이지
	private int totalRowCount;		//-- 전체갯수
	
	private int pageListSize;		//-- 한페이지에 보여줄 게시물 갯수
	private int pageTotalCount;		//-- 전체페이지 갯수
	private int pageStartRow;		//-- 게시물 시작위치 번호
	private int pateEndRow;			//-- 게시물 종료위치	 번호
	private int pageStartNum;		//-- 게시물 시작 표시 번호
	private int pageEndNum;			//-- 게시물 종료 표시 번호
	
	private int blockListSize;		//-- 한번에 보여줄 블럭갯수
	private int blockNum;			//-- 현블럭 블럭번호
	private int blockStartNum;		//-- 블럭시작번호
	private int blockEndNum;		//-- 블력종료번호
	
	public PageUtil(){
		
	}
	
	

	public PageUtil(int pageNum, int totalRowCount, int pageListSize, int blockListSize) {
		super();
		this.pageNum = pageNum;
		this.totalRowCount = totalRowCount;
		this.pageListSize = pageListSize;
		this.blockListSize = blockListSize;
		
		//-- 페이지 설정
		pageTotalCount = (int)Math.ceil(totalRowCount/(double)pageListSize);
		pageStartRow = ((pageNum - 1) * pageListSize);
		pateEndRow = pageStartRow + pageListSize;
		pageStartNum = totalRowCount - pageStartRow;
		pageEndNum = pageStartNum - pageListSize;
		if(pageEndNum < 1) pageEndNum = 1;
		
		//-- 블럭설정
		blockNum = pageNum % blockListSize == 0 ? (int)pageNum/blockListSize : ((int)pageNum/blockListSize) + 1;
		blockStartNum = ((blockNum -1) * blockListSize) + 1;
		blockEndNum = blockNum * blockListSize;
		//blockEndNum 은 pageTotalCount 보다 클수 없다
		if(blockEndNum > pageTotalCount) blockEndNum = pageTotalCount;
	}



	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public int getPageListSize() {
		return pageListSize;
	}

	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}

	public int getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public int getPateEndRow() {
		return pateEndRow;
	}

	public void setPateEndRow(int pateEndRow) {
		this.pateEndRow = pateEndRow;
	}

	public int getPageStartNum() {
		return pageStartNum;
	}

	public void setPageStartNum(int pageStartNum) {
		this.pageStartNum = pageStartNum;
	}

	public int getPageEndNum() {
		return pageEndNum;
	}

	public void setPageEndNum(int pageEndNum) {
		this.pageEndNum = pageEndNum;
	}

	public int getBlockListSize() {
		return blockListSize;
	}

	public void setBlockListSize(int blockListSize) {
		this.blockListSize = blockListSize;
	}

	public int getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	public int getBlockStartNum() {
		return blockStartNum;
	}

	public void setBlockStartNum(int blockStartNum) {
		this.blockStartNum = blockStartNum;
	}

	public int getBlockEndNum() {
		return blockEndNum;
	}

	public void setBlockEndNum(int blockEndNum) {
		this.blockEndNum = blockEndNum;
	}



	@Override
	public String toString() {
		return "PageUtil [pageNum=" + pageNum + ", totalRowCount="
				+ totalRowCount + ", pageListSize=" + pageListSize
				+ ", pageTotalCount=" + pageTotalCount + ", pageStartRow="
				+ pageStartRow + ", pateEndRow=" + pateEndRow
				+ ", pageStartNum=" + pageStartNum + ", pageEndNum="
				+ pageEndNum + ", blockListSize=" + blockListSize
				+ ", blockNum=" + blockNum + ", blockStartNum=" + blockStartNum
				+ ", blockEndNum=" + blockEndNum + "]";
	}
	
	
}
