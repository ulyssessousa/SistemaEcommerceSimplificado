/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ms.sistemaecommerce;

/**
 *
 * @author ulyss
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;

    public Produto(int id, String nome, String descricao, double preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Preço: R$" + preco;
    }
}

class ItemCarrinho {
    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return produto.getNome() + " x" + quantidade;
    }
}

class Carrinho {
    private List<ItemCarrinho> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }
}

class Pedido {
    private int id;
    private Cliente cliente;
    private Carrinho carrinho;
    private double valorTotal;

    public Pedido(int id, Cliente cliente, Carrinho carrinho) {
        this.id = id;
        this.cliente = cliente;
        this.carrinho = carrinho;
        this.valorTotal = carrinho.calcularTotal();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id + ", Cliente: " + cliente.getNome() + ", Total: R$" + valorTotal;
    }
}

class Cliente {
    private String nome;
    private String email;
    private String endereco;
    private Carrinho carrinho;

    public Cliente(String nome, String email, String endereco) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.carrinho = new Carrinho();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }
    
    public void limparCarrinho(){
        carrinho = null;
    }
    
    public void criarCarrinho(){
        carrinho = new Carrinho();
    }
}

public class SistemaEcommerce {
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static int proximoIdPedido = 1;

    public static void main(String[] args) {
        inicializarProdutos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Sistema de E-commerce!");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.print("Digite seu endereço: ");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(nome, email, endereco);

        while (true) {
            System.out.println("\nOpções:");
            System.out.println("1. Pesquisar produtos");
            System.out.println("2. Adicionar ao carrinho");
            System.out.println("3. Finalizar compra");
            System.out.println("4. Sair");
            System.out.print("Digite a opção desejada: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    pesquisarProdutos(scanner);
                    break;
                case 2:
                    adicionarAoCarrinho(cliente, scanner);
                    break;
                case 3:
                    finalizarCompra(cliente);
                    break;
                case 4:
                    System.out.println("Obrigado por comprar conosco!");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void inicializarProdutos() {
        produtos.add(new Produto(1, "Notebook", "Notebook para trabalho e estudos", 2500.00));
        produtos.add(new Produto(2, "Mouse", "Mouse sem fio", 50.00));
        produtos.add(new Produto(3, "Teclado", "Teclado mecânico", 150.00));
    }

    private static void pesquisarProdutos(Scanner scanner) {
        System.out.print("Digite o termo de pesquisa: ");
        String termo = scanner.nextLine();

        List<Produto> resultados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(termo.toLowerCase())) {
                resultados.add(produto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            System.out.println("Produtos encontrados:");
            for (Produto produto : resultados) {
                System.out.println(produto);
            }
        }
    }

    private static void adicionarAoCarrinho(Cliente cliente, Scanner scanner) {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Produto produto = null;
        for (Produto p : produtos) {
            if (p.getId() == id) {
                produto = p;
                break;
            }
        }

        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (cliente.getCarrinho() == null){
            cliente.criarCarrinho();
        }
        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        cliente.getCarrinho().adicionarItem(new ItemCarrinho(produto, quantidade));
        System.out.println("Produto adicionado ao carrinho.");
    }

    private static void finalizarCompra(Cliente cliente) {
        if (cliente.getCarrinho().getItens().isEmpty()) {
            System.out.println("Carrinho vazio. Adicione produtos antes de finalizar a compra.");
            return;
        }

        Pedido pedido = new Pedido(proximoIdPedido++, cliente, cliente.getCarrinho());
        pedidos.add(pedido);
        System.out.println("Compra finalizada com sucesso!");
        System.out.println(pedido);
        cliente.limparCarrinho(); // Limpar carrinho após a compra
    }
}
