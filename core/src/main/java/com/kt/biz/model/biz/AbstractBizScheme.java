package com.kt.biz.model.biz;

import com.kt.biz.model.AbstractScheme;
import com.kt.biz.model.ISchemeEntityAttributeRepository;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.types.BizType;
import com.kt.biz.types.CommonState;
import com.kt.entity.mysql.crm.Attribute;
import com.kt.entity.mysql.crm.Biz;
import com.kt.entity.mysql.crm.BizAttribute;
import com.kt.repo.mysql.batch.BizAttributeRepository;
import com.kt.repo.mysql.batch.BizRepository;
import com.kt.sys.SpringContextHolder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public abstract class AbstractBizScheme extends AbstractScheme<Biz> {

    @Override
    public Class<? extends Attribute> getAttributeClass() {
        return BizAttribute.class;
    }

    @Override
    public Biz getInitializedEntity() {
        Biz biz = new Biz();
        biz.setState(CommonState.DRAFT.toString());
        return biz;
    }

    @Override
    protected ISchemeEntityAttributeRepository getAttributeRepository() {
        return SpringContextHolder.getBean(BizAttributeRepository.class);
    }

    @Override
    protected JpaRepository<Biz, String> getEntityRepository() {
        return SpringContextHolder.getBean(BizRepository.class);
    }

    @Override
    public AbstractBizScheme instantiate() {
        AbstractBizScheme newInstance = BizSchemeManager.newInstance((BizType) getType());
        if (newInstance != null) {
            newInstance.entity = cloneEntity();
            newInstance.entity.setIsTemplate(0);
            newInstance.entity.setState(CommonState.DRAFT.toString());
            newInstance.copyFrom(this);
        }
        return newInstance;
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        return CompletionCheckResult.buildOkResult();
    }

    @Override
    public abstract BizType getType();

    public abstract void copyFrom(AbstractBizScheme scheme);



    public void setIsTemplate(boolean isTemplate) {
        if (isTemplate) {
            entity.setIsTemplate(1);
        } else {
            entity.setIsTemplate(0);
        }
    }

    public boolean isTemplate() {
        return entity.getIsTemplate() == 1;
    }

    public CommonState getState() {
        return CommonState.valueOf(entity.getState());
    }

    public void setState(CommonState state) {
        entity.setState(state.toString());
    }

    public Biz getEntity() {
        return entity;
    }
}
