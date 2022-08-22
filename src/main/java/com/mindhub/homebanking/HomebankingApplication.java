package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication //indica que es un projecto que lo maneja Spring
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	//Instanciamos el repositorio
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository)  {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Ibrian", "Festorazzi", "ibrian@gmail.com",  passwordEncoder.encode("1234"));
			Client client2= new Client ("Juan", "Juanjo", "juan@gmail.com",  passwordEncoder.encode("1234"));

			clientRepository.save(client1);
			clientRepository.save(client2);




			// save a couple of Accounts
			Account account1 = new Account("VIN-4567899", 2000.00, client1);
			Account account2= new Account("VIN-6767899", 3454.09, client2);


			accountRepository.save(account1);
			accountRepository.save(account2);

/*
			Transaction transaction3= new Transaction(TransactionType.CREDIT, 200.44,"prueba1");
			Transaction transaction4= new Transaction(TransactionType.DEBIT, -100.00,"prueba2");


			account1.addTransaction(transaction3);
			account1.addTransaction(transaction4);


			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
*/
			List<Integer> paymentsHipotecario = new ArrayList<>();
			paymentsHipotecario.add(12);
			paymentsHipotecario.add(24);
			paymentsHipotecario.add(36);
			paymentsHipotecario.add(48);
			paymentsHipotecario.add(60);

			List<Integer> paymentsPersonal= new ArrayList<>();
			paymentsPersonal.add(6);
			paymentsPersonal.add(12);
			paymentsPersonal.add(24);

			List<Integer> paymentsAutomotriz= new ArrayList<>();
			paymentsAutomotriz.add(6);
			paymentsAutomotriz.add(12);
			paymentsAutomotriz.add(24);
			paymentsAutomotriz.add(36);




			Loan loan1=new Loan("Hipotecario",500000.00,paymentsHipotecario);
			Loan loan2=new Loan("Personal", 100000.00,paymentsPersonal);
			Loan loan3=new Loan("Automotriz", 300000.00,paymentsAutomotriz);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);


			//ClientLoan clientLoan1= new ClientLoan(20000.00, 6, client1,loan3);
			//ClientLoan clientLoan2=new ClientLoan(100000.00,12,client1,loan2);
			//clientLoanRepository.save(clientLoan1);
			//clientLoanRepository.save(clientLoan2);

			/*
			Card card1=new Card("0665 2234 4392 2388", 123, CardType.DEBIT, CardColor.GOLD, client1);
			Card card2=new Card(CardType.CREDIT,CardColor.TITANIUM,client1);

			cardRepository.save(card1);
			cardRepository.save(card2);
			*/


		};
	}
}
