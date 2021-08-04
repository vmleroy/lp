-- 9)
-- Verificar se uma lista de inteiros é palíndromo.
-- Ex.: > palindromo [1,2,3,4,3,2,1]
-- True

reverter :: [Integer] -> [Integer]
reverter [] = []
reverter (a:l) = (reverter l) ++ [a]

palindromo :: [Integer] -> Bool
palindromo l
            |  l == reverter(l) = True
            | otherwise = False