-- 13)
-- Remover os n últimos elementos de uma lista de inteiros.
-- Ex.: > removerFim 2 [1,2,3,4,5,6] -- n=2
-- [1,2,3,4]

removerFim :: Int -> [Integer] -> [Integer]
removerFim _ [] = []
removerFim 0 l = l
removerFim n (x:l)
    | n > length(l) = []
    | length(l) > n = [x] ++ (removerFim n l)
    | otherwise = [x]

