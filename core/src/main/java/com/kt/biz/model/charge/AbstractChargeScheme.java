package com.kt.biz.model.charge;

import com.kt.biz.model.AbstractScheme;
import com.kt.biz.model.ISchemeEntityAttributeRepository;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.CommonState;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.crm.Attribute;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.ChargeSchemeAttribute;
import com.kt.entity.mysql.crm.Order;
import com.kt.repo.mysql.batch.ChargeSchemeAttributeRepository;
import com.kt.repo.mysql.batch.ChargeSchemeRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.sys.SpringContextHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
public abstract class AbstractChargeScheme extends AbstractScheme<ChargeScheme> {

    @Override
    public Class<? extends Attribute> getAttributeClass() {
        return ChargeSchemeAttribute.class;
    }

    @Override
    public ChargeScheme getInitializedEntity() {
        ChargeScheme scheme = new ChargeScheme();
        scheme.setState(CommonState.DRAFT.toString());
        return scheme;
    }

    @Override
    protected ISchemeEntityAttributeRepository getAttributeRepository() {
        return SpringContextHolder.getBean(ChargeSchemeAttributeRepository.class);
    }

    @Override
    protected JpaRepository<ChargeScheme, String> getEntityRepository() {
        return SpringContextHolder.getBean(ChargeSchemeRepository.class);
    }

    protected Order findOrder() {
        return getRepository(OrderRepository.class).findOneByChargeId(entity.getId());
    }

    @Override
    public AbstractChargeScheme instantiate() {
        AbstractChargeScheme newInstance = ChargeSchemeManager.newInstance(getType());
        if (newInstance != null) {
            newInstance.entity = cloneEntity();
            newInstance.entity.setIsTemplate(0);
            newInstance.entity.setState(CommonState.DRAFT.toString());
            newInstance.copyChargeElementFrom(this);
        }
        return newInstance;
    }

    @Override
    public abstract ChargeType getType();

    /**
     * Copy the charge part attributes from the scheme template, such as unit price
     * @param scheme the template to be copied from
     */
    public abstract void copyChargeElementFrom(AbstractChargeScheme scheme);

    public abstract void copyUserInputElementFrom(AbstractChargeScheme scheme);

    public abstract Date calculateEndDate(Date startDate);

    public abstract double calculateFirstInstallment(Date startDate, PayInterval payInterval);

    public abstract double calculateTotalAmount(Date start, Date end);

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
}
