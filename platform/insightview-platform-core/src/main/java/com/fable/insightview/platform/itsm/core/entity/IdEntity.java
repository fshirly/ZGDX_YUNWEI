package com.fable.insightview.platform.itsm.core.entity;

/**
 * 含有ID主键的数据实体抽象类
 * 
 * @author 汪朝  2013-9-30
 */
public abstract class IdEntity extends Entity {

    private static final long serialVersionUID = -1479585474644721370L;

    /**
     * 获取主键
     * @return 主键
     */
    public abstract Long getId();

    /**
     * 设置主键
     * @param id 主键
     */
    public abstract void setId(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IdEntity other = (IdEntity) obj;
        if (this.getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }

}
