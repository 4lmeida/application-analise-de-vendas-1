package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> list = new ArrayList<>();
			
			String lines = br.readLine();
			while(lines != null) {
				String[] fieldes = lines.split(",");
				list.add(new Sale(Integer.parseInt(fieldes[0]),
								  Integer.parseInt(fieldes[1]), 
								  fieldes[2], 
								  Integer.parseInt(fieldes[3]), 
								  Double.parseDouble(fieldes[4])));
				
				lines = br.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preco medio");
			
			Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
			
			List<Sale> listUpdate = list.stream()
						.filter(x -> x.getYear() == 2016)
						.sorted(comp.reversed())
						.limit(5)
						.collect(Collectors.toList());
			
			listUpdate.forEach(System.out::println);
			
			System.out.println();
			
			
			double sum1 = list.stream()
					.filter(x -> x.getSeller().charAt(0) == 'L')
					.filter(x -> x.getMonth() == 1)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
					
			double sum7 = list.stream()
					.filter(x -> x.getSeller().charAt(0) == 'L')
					.filter(x -> x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
					
			
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " 
					+ String.format("%.2f", sum1 + sum7));
			
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage()); 
		}
		
		sc.close();

	}

}
