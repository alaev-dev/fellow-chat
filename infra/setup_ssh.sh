#!/bin/bash

# Check if all required arguments are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <username@host.com> <your_email@example.com>"
    exit 1
fi

# Step 1: Generate an SSH Key
ssh_key_file="github-actions"
ssh_server="$1"
email_address="$2"

if [ ! -f ~/.ssh/"$ssh_key_file" ]; then
    # Generate SSH key locally
    ssh-keygen -t rsa -b 4096 -C "$email_address" -f ~/.ssh/"$ssh_key_file" -N ""
fi

# Step 2: Copy the Public and Private Keys to the Server and Add the Public Key to authorized_keys
# shellcheck disable=SC2088
ssh_key_path="~/.ssh"

# Copy the public and private keys to the server
ssh "$ssh_server" "mkdir -p $ssh_key_path"
scp ~/.ssh/"$ssh_key_file" "$ssh_server:$ssh_key_path"
scp ~/.ssh/"$ssh_key_file.pub" "$ssh_server:$ssh_key_path"

# Add the public key to authorized_keys on the server
ssh "$ssh_server" "cat $ssh_key_path/$ssh_key_file.pub >> $ssh_key_path/authorized_keys"