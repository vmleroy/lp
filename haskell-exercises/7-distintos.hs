-- 7)
-- Verificar se todos os elementos de uma lista são distintos.
-- Ex.: > distintos [1,2,4,2,5]
-- False
-- > distintos [3,2,1]
-- True


distintos :: [Integer] -> Bool
distintos [] = True
distintos (x:y)
 | igualNoArray x y = False
 | otherwise = distintos y

igualNoArray :: Integer -> [Integer] -> Bool
igualNoArray x [] = False
igualNoArray x (y:l)
 | x == y = True
 | otherwise = igualNoArray x l
