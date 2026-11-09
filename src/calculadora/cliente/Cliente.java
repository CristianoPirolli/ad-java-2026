package calculadora.cliente;

import calculadora.comum.Calculadora; // <- Importa a interface
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
    public static void main(String[] args) {
        try {
            String ipServidor = (args.length > 0) ? args[0] : "localhost";
            System.out.println("Conectando ao calculadora.servidor em: " + ipServidor);

            Registry registry = LocateRegistry.getRegistry(ipServidor, 1099);
            Calculadora calc = (Calculadora) registry.lookup("ServicoCalculadora");

            System.out.println("15 + 25 = " + calc.somar(15, 25));
            System.out.println("40 - 18 = " + calc.subtrair(40, 18));
            System.out.println("6 * 7 = " + calc.multiplicar(6, 7));
            System.out.println("7 / 2 = " + calc.dividir(7, 2));

            // Exemplo de erro tratado: divisão por zero lançada pelo servidor
            try {
                System.out.println("10 / 0 = " + calc.dividir(10, 0));
            } catch (ArithmeticException e) {
                System.out.println("10 / 0 -> Erro retornado pelo servidor: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Erro no calculadora.cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
