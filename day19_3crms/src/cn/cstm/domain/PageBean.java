package cn.cstm.domain;

import java.util.List;

public class PageBean<T> {
	private int pc;//pageCode����ǰҳ��
	private int tr;//totalRecord, �ܼ�¼��
	private int ps;//pagesize, ÿҳ��¼��
//	private int tp;//totalPage, ��ҳ��-->����ó�
	private List<T> beanList;//��ǰҳ��¼
	
	private String url;//��������������
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * ������ҳ��
	 */
	public int getTp() {
		int tp = tr / ps;
		return tr % ps == 0 ? tp : tp + 1;
	}
	
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}


}
