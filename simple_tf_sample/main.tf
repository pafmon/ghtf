provider "aws" {
  region = "us-east-2"
}

resource "aws_instance" "testing" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
  vpc_security_group_ids = [aws_security_group.instance.id]

  tags = {
    Name = "terraform-testing"
  }
}

resource "aws_security_group" "instance" {
  name = "terraform-testing-instance"
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "public_ip" {
  value       = aws_instance.testing.public_ip
  description = "The public IP of the web server"
}