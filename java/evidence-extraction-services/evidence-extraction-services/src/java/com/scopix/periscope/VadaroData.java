package com.scopix.periscope;

import java.util.Date;

public class VadaroData implements Comparable<VadaroData>{

	private Integer in;
	private Integer out;
	private Date formattedDate;

	public Integer getIn() {
		return in;
	}
	
	public void setIn(Integer in) {
		this.in = in;
	}
	
	public Integer getOut() {
		return out;
	}
	
	public void setOut(Integer out) {
		this.out = out;
	}

	public Date getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(Date formattedDate) {
		this.formattedDate = formattedDate;
	}

    @Override
    public int compareTo(VadaroData vadaroData) {
        if(this.getFormattedDate()!=null){
            Date formattedDate2 = vadaroData.getFormattedDate();

            if(formattedDate2!=null){
                if(this.getFormattedDate().before(formattedDate2)){
                    return -1;
                }else if(this.getFormattedDate().after(formattedDate2)){
                    return 1;
                }
            }
        }
        return 0;
    }
}