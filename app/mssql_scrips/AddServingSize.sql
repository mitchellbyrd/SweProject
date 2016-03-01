if objectproperty(object_id('dbo.AddServingSize'), N'IsProcedure') = 1
	drop procedure dbo.AddServingSize
go

create procedure dbo.AddServingSize
	@servingSizeType varchar(300)
,	@amount float
,	@calories float
,	@proteins float
,	@carbs float
,	@fats float
,	@foodId int
as 
	set nocount on

	insert into dbo.ServingSizes (
		ServingSizeType
	,	Amount
	,	Calories
	,	Proteins
	,	Carbs
	,	Fats
	,	FoodId )
	values (
		@servingSizeType
	,	@amount
	,	@calories
	,	@proteins
	,	@carbs
	,	@fats
	,	@foodId )

	select 
		ss.Id
	from
		dbo.ServingSizes ss
	where
		ss.ServingSizeType = @servingSizeType
		and ss.Amount = @amount
		and ss.Calories = @calories
		and ss.Proteins = @proteins
		and ss.Carbs = @carbs
		and ss.Fats = @fats
		and ss.FoodId = @foodId

go
