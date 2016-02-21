use master;

with MacrosPer100Grams as(
	select
		[NDB_No]
	,	p.[203] as 'Carbs'
	,	p.[204] as 'Fats'
	,	p.[205] as 'Proteins'
	from 
		[SR-28].dbo.NUT_DATA nd
	pivot(
		sum(nd.Nutr_Val)
		for nd.Nutr_No in ( [203], [204], [205] )
	) as p
)
select
	fd.NDB_No
,	w.Amount as 'ServingSize Amount'
,	w.Msre_Desc as ServingSizeType
,	(w.Gm_Wgt / 100) * mpg.Carbs as 'Carbs'
,	(w.Gm_Wgt / 100) * mpg.Fats as 'Fats'
,	(w.Gm_Wgt / 100) * mpg.Proteins as 'Proteins'
from 
	[SR-28].dbo.WEIGHT w
	inner join MacrosPer100Grams mpg
		on w.NDB_No = mpg.NDB_No
	inner join [SR-28].dbo.FOOD_DES fd
		on fd.NDB_No = w.NDB_No