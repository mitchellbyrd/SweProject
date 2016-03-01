if objectproperty(object_id('dbo.AddFood'), N'IsProcedure') = 1
	drop procedure dbo.AddFood
go

create procedure dbo.AddFood
	@foodName varchar(300)
as 
	set nocount on

	insert into dbo.Foods(
		Name)
	values (
		@foodName )

	select 
		f.Id
	from
		dbo.Foods f
	where 
		f.Name = @foodName
go
