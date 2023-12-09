resource "aws_vpc" "dockert2s_vpc_1" {
  cidr_block = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support = true

  tags = {
    "Name" = "dockert2s_vpc_1"
  }
}

resource "aws_subnet" "dockert2s_subnet_1" {
  vpc_id     = aws_vpc.dockert2s_vpc_1.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "us-east-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "dockert2s_subnet_pub"
  }
}

resource "aws_internet_gateway" "dockert2s_gateway" {
  vpc_id = aws_vpc.dockert2s_vpc_1.id

  tags = {
    "Name" = "dockert2s_gateway"
  }
}

resource "aws_route_table" "dockert2s_route_table" {
  vpc_id         = aws_vpc.dockert2s_vpc_1.id

  tags = {
    "Name" = "dockert2s_route_table"
  }
}

resource "aws_route" "dockert2s_route" {
  route_table_id = aws_route_table.dockert2s_route_table.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id = aws_internet_gateway.dockert2s_gateway.id
}

resource "aws_route_table_association" "dockert2s_route_table_association" {
  route_table_id = aws_route_table.dockert2s_route_table.id
  subnet_id = aws_subnet.dockert2s_subnet_1.id
}

resource "aws_instance" "dockert2s_ec2" {
  instance_type = "t2.micro"
  key_name = aws_key_pair.dockert2s_keypair.id
  vpc_security_group_ids = [aws_security_group.dockert2s_security_group.id]
  subnet_id = aws_subnet.dockert2s_subnet_1.id

  ami = data.aws_ami.dockert2s_ami.id

  root_block_device {
    volume_size = 0
  }

  tags = {
    Name="dockert2s_ec2"
  }

  user_data = file("userdata.tpl")


}