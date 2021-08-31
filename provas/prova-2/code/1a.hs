-- "Dada uma lista de inteiros, determinar se existem e quais são os buracos dessa sequência de valores."

-- > game [3, 1, 4, 6, 5]
--   [2]
-- > game [3, 1, 2, 4]
--   []
-- > game [2, 6, 7, 5]
--   [3, 4]

-- Auxiliares
tamanho :: [Int] -> Int
tamanho [] = 0
tamanho (a:x) = 1 + tamanho x

qsort :: [Int] -> [Int]
qsort [] = []
qsort (a:x) = qsort [ b | b <- x, b <= a ] ++ [a] ++ qsort [ b | b <- x, b > a ]

-- Execucao do jogo
falta :: Int -> Int -> [Int]
falta x y
  | x+1 == y = []
  | x+1 < y = [x+1] ++ falta (x+1) y
  | otherwise = []

execucao :: [Int] -> [Int]
execucao [] = []
execucao (x:y:l)
  | tamanho(y:l) > 1 = (falta x y) ++ execucao (y:l)
  | otherwise = falta x y ++ execucao l

game :: [Int] -> [Int]
game l = execucao (qsort l)

