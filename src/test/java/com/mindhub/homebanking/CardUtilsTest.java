package com.mindhub.homebanking;


import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase()
public class CardUtilsTest {

    @Test
    public void cardNumberCreated(){
        String cardNumber = CardUtils.getCardNumber(1001,10000);
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void CVVIsCreated(){
        int cvv = CardUtils.getCVVNumber(101,1000);
        assertThat(cvv,is(not(0)));
        //assertThat(cvv,lessThan());
    }
}
