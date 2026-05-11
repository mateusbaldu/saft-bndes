package avancado.poo.saft_bndes.converters;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractBeanField<BigDecimal, String> {

    @Override
    protected BigDecimal convert(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return new BigDecimal(value.trim().replace(".", "").replace(",", "."));
    }
}
