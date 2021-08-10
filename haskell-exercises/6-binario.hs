-- 6)
-- Função que retorna uma lista com a representação em binário de um número inteiro.
-- Ex.: > binario 20
-- [1,0,1,0,0]

binario :: Integer -> [Integer]
binario x
    | x == 1 = [1]
    | x == 0 = [0]
    | x `mod` 2 == 0 = (binario (x `div` 2)) ++ [0]
    | otherwise = (binario (x `div` 2)) ++ [1]
