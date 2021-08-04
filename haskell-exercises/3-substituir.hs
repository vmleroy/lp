-- 3)
-- Substituir todos elementos de um determinado valor de uma lista de inteiros por um outro valor.
-- Ex.: > substituir 1 0 [1,2,1,3,1]
-- [0,2,0,3,0]

substituir :: Integer -> Integer -> [Integer] -> [Integer]
substituir x y [] = []
substituir x y (a:b)
 | a == x = (y:(substituir x y b))
 | otherwise = (a:(substituir x y b)) 
