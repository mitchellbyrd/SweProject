use master;


insert into
	CalorieCounter.dbo.Foods(
		Name
	,	Usda_Id)
select 
	fd.Long_Desc
,	fd.NDB_No
from 
	[SR-28].dbo.FOOD_DES fd;


with MacrosPer100Grams as(
	select
		[NDB_No]
	,	p.[203] as 'Carbs'
	,	p.[204] as 'Fats'
	,	p.[205] as 'Proteins'
	from 
		[SR-28].dbo.NUT_DATA nd
	pivot(
		max(nd.Nutr_Val)
		for nd.Nutr_No in ( [203], [204], [205] )
	) as p
)
insert into 
	CalorieCounter.dbo.ServingSizes(
		ServingSizeType
	,	Amount
	,	Calories
	,	Proteins
	,	Carbs
	,	fats
	,	FoodId)
select
	w.Msre_Desc --as ServingSizeType
,	w.Amount --as 'ServingSize Amount'
,	((((w.Gm_Wgt / 100) * mpg.Proteins) + ((w.Gm_Wgt / 100) * mpg.Carbs)) * 4) + (((w.Gm_Wgt / 100) * mpg.Fats) * 9)
,	(w.Gm_Wgt / 100) * mpg.Proteins as 'Proteins'
,	(w.Gm_Wgt / 100) * mpg.Carbs as 'Carbs'
,	(w.Gm_Wgt / 100) * mpg.Fats as 'Fats'
,	f.Id
from 
	[SR-28].dbo.WEIGHT w
	inner join MacrosPer100Grams mpg
		on w.NDB_No = mpg.NDB_No
	inner join [SR-28].dbo.FOOD_DES fd
		on fd.NDB_No = w.NDB_No
	inner join CalorieCounter.dbo.Foods f
		on fd.NDB_No = f.Usda_Id

		select * from CalorieCounter.dbo.ServingSizes