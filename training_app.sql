-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 06-Fev-2023 às 11:11
-- Versão do servidor: 10.4.25-MariaDB
-- versão do PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `training_app`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `conjunto_exercicios`
--

CREATE TABLE `conjunto_exercicios` (
  `id_conjunto` int(11) NOT NULL,
  `id_exercicio` int(11) NOT NULL,
  `repeticoes` int(11) DEFAULT NULL,
  `tempo` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `conjunto_exercicios`
--

INSERT INTO `conjunto_exercicios` (`id_conjunto`, `id_exercicio`, `repeticoes`, `tempo`) VALUES
(1, 1, 10, NULL),
(1, 2, 20, NULL),
(1, 3, NULL, '00:01:00'),
(1, 5, NULL, '00:02:00'),
(1, 11, NULL, '00:01:00'),
(1, 12, NULL, '00:01:00'),
(1, 13, NULL, '00:00:30'),
(1, 28, 15, NULL),
(2, 4, NULL, '00:02:00'),
(2, 20, NULL, '00:01:00'),
(2, 23, NULL, '00:00:45'),
(2, 31, 20, NULL),
(2, 32, 20, NULL),
(2, 33, 15, NULL),
(3, 1, 10, NULL),
(3, 3, 15, NULL),
(3, 5, 20, NULL),
(3, 11, 10, NULL),
(3, 12, 15, NULL),
(3, 13, 10, NULL),
(3, 26, 25, NULL),
(3, 27, 20, NULL),
(3, 28, 10, NULL),
(3, 29, 30, NULL),
(3, 30, 25, NULL),
(4, 6, 20, NULL),
(4, 7, NULL, '00:01:00'),
(4, 8, 20, NULL),
(4, 9, 15, NULL),
(4, 10, 25, NULL),
(5, 4, NULL, '00:02:00'),
(5, 20, NULL, '00:01:00'),
(5, 23, NULL, '00:00:45'),
(5, 34, 20, NULL),
(5, 35, 20, NULL),
(5, 36, 25, NULL),
(5, 37, 15, NULL),
(6, 14, 20, NULL),
(6, 15, 15, NULL),
(6, 16, 20, NULL),
(6, 17, 20, NULL),
(6, 18, 15, NULL),
(6, 24, 15, NULL),
(6, 25, 10, NULL),
(7, 19, NULL, '00:01:00'),
(7, 20, NULL, '00:01:00'),
(7, 21, NULL, '00:01:30'),
(7, 22, NULL, '00:01:00'),
(7, 23, NULL, '00:00:45'),
(8, 37, 12, NULL),
(9, 23, NULL, '00:00:45');

-- --------------------------------------------------------

--
-- Estrutura da tabela `exercicios`
--

CREATE TABLE `exercicios` (
  `id_exercicio` int(11) NOT NULL,
  `nome` varchar(40) DEFAULT NULL,
  `zona` varchar(30) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `dificuldade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `exercicios`
--

INSERT INTO `exercicios` (`id_exercicio`, `nome`, `zona`, `tipo`, `dificuldade`) VALUES
(1, 'flexão de braço', 'Braço,Peito', 'massa', 3),
(2, 'Afundar Triceps com a cadeira', 'Braço', 'massa', 3),
(3, 'Flexão na parede', 'Braço,Peito', 'massa', 2),
(4, 'Saltar à corda', 'Braço,Perna', 'peso', 3),
(5, 'Flexão com os joelhos', 'Braço,Peito', 'massa', 2),
(6, 'Agachamento', 'Pernas', 'massa', 2),
(7, 'Pulo lateral', 'Pernas', 'massa', 3),
(8, 'Abdominal a fundo', 'Pernas', 'massa', 3),
(9, 'Elevação lateral das pernas', 'Pernas', 'massa', 3),
(10, 'Pontapé de burro', 'Pernas', 'massa', 2),
(11, 'Flexão com um braço', 'Braço,Peito', 'massa', 5),
(12, 'Flexão com braços abertos', 'Braço,Peito', 'massa', 4),
(13, 'Flexão diamante', 'Braço,Peito', 'massa', 4),
(14, 'Abdominal', 'Abdominal', 'massa', 3),
(15, 'Montanha', 'Abdominal', 'massa', 3),
(16, 'Toque de Calcanhar', 'Abdominal', 'massa', 3),
(17, 'Abdominal russo', 'Abdominal', 'massa', 4),
(18, 'Elevação de pernas', 'Abdominal', 'massa', 3),
(19, 'Polichinelos', 'Pernas, Abdominal', 'peso', 3),
(20, 'Burpees', 'Abdominal,Pernas,Braço', 'peso', 4),
(21, 'Skipping', 'Abdominal', 'peso', 3),
(22, 'Bicicleta no ar', 'Abdominal', 'peso', 2),
(23, 'Prancha', 'Abdominal,Pernas,Braço', 'peso', 3),
(24, 'Abdominal em V', 'Abdominal', 'massa', 4),
(25, 'Abdominal com braço estendido', 'Abdominal', 'massa', 5),
(26, 'Supino reto com alteres', 'peito', 'massa', 2),
(27, 'Supino reto com barra', 'peito', 'massa', 2),
(28, 'Peito nas paralelas', 'peito, braço', 'massa', 3),
(29, 'Crucifixo no voador', 'peito', 'massa', 1),
(30, 'Voador no cabo com banco deitado', 'peito', 'massa', 2),
(31, 'Elevação frontal com alteres', 'braço', 'peso', 2),
(32, 'Elevações de barra fixa', 'braço', 'peso', 4),
(33, 'Rosca com barra de pego fechado', 'braço', 'peso', 3),
(34, 'Agachamento Isométrico', 'pernas', 'peso', 2),
(35, 'Agachamento Búlgaro', 'pernas', 'peso', 3),
(36, 'Cadeira extensora', 'pernas', 'peso', 2),
(37, 'Saltar a corda', 'pernas', 'peso', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `plano_treino`
--

CREATE TABLE `plano_treino` (
  `id_treino` int(11) NOT NULL,
  `id_conjunto` int(11) NOT NULL,
  `dia` varchar(25) DEFAULT NULL,
  `tipo` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `plano_treino`
--

INSERT INTO `plano_treino` (`id_treino`, `id_conjunto`, `dia`, `tipo`) VALUES
(1, 1, 'Domingo', 'Perder Peso'),
(1, 2, 'Segunda', 'Perder Peso'),
(1, 3, 'Terça', 'Perder Peso'),
(1, 4, 'Quarta', 'Perder Peso'),
(1, 5, 'Quinta', 'Perder Peso'),
(1, 6, 'Sexta', 'Perder Peso'),
(1, 7, 'Sabado', 'Perder Peso'),
(2, 1, 'Domingo', 'ganhar massa'),
(2, 2, 'Segunda', 'ganhar massa'),
(2, 3, 'Terça', 'ganhar massa'),
(2, 4, 'Quarta', 'ganhar massa'),
(2, 5, 'Quinta', 'ganhar massa'),
(2, 6, 'Sexta', 'ganhar massa'),
(2, 7, 'Sabado', 'ganhar massa');

-- --------------------------------------------------------

--
-- Estrutura da tabela `utilizador`
--

CREATE TABLE `utilizador` (
  `id_user` int(11) NOT NULL,
  `id_treino` int(11) DEFAULT NULL,
  `nome` varchar(30) NOT NULL,
  `peso` decimal(5,2) NOT NULL,
  `altura` decimal(3,2) NOT NULL,
  `objetivo` varchar(25) NOT NULL,
  `passe` varchar(30) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `utilizador`
--

INSERT INTO `utilizador` (`id_user`, `id_treino`, `nome`, `peso`, `altura`, `objetivo`, `passe`, `admin`) VALUES
(1, 2, 'José', '75.00', '1.75', 'ganhar massa', 'user123', 0),
(2, 1, 'Admin', '10.00', '2.00', 'Perder peso', 'admin345', 1),
(4, 1, 'joao', '62.00', '1.62', 'Perder peso', 'adeusola', 0),
(3, 2, 'user', '75.00', '1.75', 'ganhar massa', 'user123', 0),
(5, 1, 'useracc2', '60.00', '1.75', 'Perder peso', 'useracc', 0),
(6, 2, 'user3', '60.00', '1.65', 'ganhar massa', 'test', 0);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `conjunto_exercicios`
--
ALTER TABLE `conjunto_exercicios`
  ADD PRIMARY KEY (`id_conjunto`,`id_exercicio`),
  ADD KEY `id_exercicio` (`id_exercicio`);

--
-- Índices para tabela `exercicios`
--
ALTER TABLE `exercicios`
  ADD PRIMARY KEY (`id_exercicio`);

--
-- Índices para tabela `plano_treino`
--
ALTER TABLE `plano_treino`
  ADD PRIMARY KEY (`id_treino`,`id_conjunto`),
  ADD KEY `id_conjunto` (`id_conjunto`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `conjunto_exercicios`
--
ALTER TABLE `conjunto_exercicios`
  MODIFY `id_conjunto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `exercicios`
--
ALTER TABLE `exercicios`
  MODIFY `id_exercicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de tabela `plano_treino`
--
ALTER TABLE `plano_treino`
  MODIFY `id_treino` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
