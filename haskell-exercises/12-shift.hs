-- 12)
-- Deslocar todos elementos de uma lista de inteiros k posições para a esquerda.
-- Ex.: > shift 3 [1,5,6,7,3,4,1] -- k=3
-- [7,3,4,1,1,5,6] 

shift :: Int -> [Int] -> [Int]
shift 0 l = l
shift k (x:l) = (shift (k-1) (l ++ [x]))