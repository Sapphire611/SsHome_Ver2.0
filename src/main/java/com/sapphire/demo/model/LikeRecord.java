package com.sapphire.demo.model;

public class LikeRecord {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column likeRecord.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column likeRecord.userId
     *
     * @mbg.generated
     */
    private Long userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column likeRecord.questionId
     *
     * @mbg.generated
     */
    private Long questionid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column likeRecord.gmtCreate
     *
     * @mbg.generated
     */
    private Long gmtcreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column likeRecord.id
     *
     * @return the value of likeRecord.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column likeRecord.id
     *
     * @param id the value for likeRecord.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column likeRecord.userId
     *
     * @return the value of likeRecord.userId
     *
     * @mbg.generated
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column likeRecord.userId
     *
     * @param userid the value for likeRecord.userId
     *
     * @mbg.generated
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column likeRecord.questionId
     *
     * @return the value of likeRecord.questionId
     *
     * @mbg.generated
     */
    public Long getQuestionid() {
        return questionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column likeRecord.questionId
     *
     * @param questionid the value for likeRecord.questionId
     *
     * @mbg.generated
     */
    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column likeRecord.gmtCreate
     *
     * @return the value of likeRecord.gmtCreate
     *
     * @mbg.generated
     */
    public Long getGmtcreate() {
        return gmtcreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column likeRecord.gmtCreate
     *
     * @param gmtcreate the value for likeRecord.gmtCreate
     *
     * @mbg.generated
     */
    public void setGmtcreate(Long gmtcreate) {
        this.gmtcreate = gmtcreate;
    }
}