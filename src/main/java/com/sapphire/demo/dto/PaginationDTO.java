package com.sapphire.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questions;
    private List<ReplyDTO> replies;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;
    private Integer totalCount = 0;
    
    

    public List<ReplyDTO> getReplies() {
		return replies;
	}



	public void setReplies(List<ReplyDTO> replies) {
		this.replies = replies;
	}



	public List<QuestionDTO> getQuestions() {
		return questions;
	}



	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}



	public Boolean getShowPrevious() {
		return showPrevious;
	}



	public void setShowPrevious(Boolean showPrevious) {
		this.showPrevious = showPrevious;
	}



	public Boolean getShowFirstPage() {
		return showFirstPage;
	}



	public void setShowFirstPage(Boolean showFirstPage) {
		this.showFirstPage = showFirstPage;
	}



	public Boolean getShowNext() {
		return showNext;
	}



	public void setShowNext(Boolean showNext) {
		this.showNext = showNext;
	}



	public Boolean getShowEndPage() {
		return showEndPage;
	}



	public void setShowEndPage(Boolean showEndPage) {
		this.showEndPage = showEndPage;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public List<Integer> getPages() {
		return pages;
	}



	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}



	public Integer getTotalPage() {
		return totalPage;
	}



	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}



	public Integer getTotalCount() {
		return totalCount;
	}



	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}



	/**
     @param totalCount = 总问题数
     @param page = 当前页面
     @param size = 单页问题容量
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.totalCount = totalCount; // 个人总问题数，用于输出到Badge

        // 根据总问题数和单页容量计算出页面数
        if(totalCount % size == 0){
            this.totalPage = totalCount / size;
        }else{
            this.totalPage = totalCount / size + 1;
        }


        // 当前页小于1或者大于totalPage时,做出修正
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        this.page = page;

        pages.add(page);
        /*排除当前页= 1，2，3，end-1,end-2,end-3 的页码前后情况*/
        for (int i = 1; i <= 3; i++) {
            if(page - i > 0){
                pages.add(0,page - i); // 从头部插入
            }

            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }
        // 是否显示上一页 (<)
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }

        // 是否显示下一页 (>)
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        // 是否展示第一页 (<<)
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        // 是否展示最后一页 (>>)
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
