    The Main three entity in the system are satellite, device, and files
with there inheritance unique attribute. The relay satellite does not have the bandwidth or any storage. thus, I create Satellites without bandwidth and storage. Both standard satellite and shriking satellite has there own bandwidth and storage type. 

    File have different state and value need to be tracking between 
transferring. I have designed to create fileTransferable<Interface> and 
PartialFil<extends Files>. These two type tracking some attribute and also give ability for the entity to transfer file from each other. Partial File needs to track sender and reciever. fileTransferable not only give ability to transfer file, but also to track number of sending and recieving file to determine the availiblity and the bandwidth of the entity if they has one. 

    QuantumCompressor<class> is stored in every shriking satellite to use
quantumCompression when storing the file to the satellite storage. and has aggregation or has a relationship. 


    Multiple data need to be stored to simulate the interaction and 
behaviour of the entity in the system. thus, I have Designed Simulations<Class> to store list of entity and its Ids. We can manipulate the entity, move the entity, and make them interact with each other using there method or the simulation method. Movable also implements in the satellite to make the entity moveable depends on their position height and linear velocity. 

note
- some of the attribute does not have setter due to it is charactersitic of specific class such as the max range or storage size of satellite.
