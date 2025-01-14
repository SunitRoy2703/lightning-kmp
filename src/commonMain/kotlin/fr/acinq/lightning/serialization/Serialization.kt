package fr.acinq.lightning.serialization

import fr.acinq.bitcoin.ByteVector32
import fr.acinq.bitcoin.PrivateKey
import fr.acinq.lightning.NodeParams
import fr.acinq.lightning.wire.EncryptedChannelData

object Serialization {

    fun serialize(state: fr.acinq.lightning.channel.ChannelStateWithCommitments): ByteArray {
        return fr.acinq.lightning.serialization.v2.Serialization.serialize(state)
    }

    fun deserialize(bin: ByteArray, nodeParams: NodeParams): fr.acinq.lightning.channel.ChannelStateWithCommitments {
        return try {
            fr.acinq.lightning.serialization.v2.Serialization.deserialize(bin, nodeParams)
        } catch (e: Throwable) {
            // try legacy format
            fr.acinq.lightning.serialization.v1.Serialization.deserialize(bin, nodeParams)
        }
    }

    fun encrypt(key: ByteVector32, state: fr.acinq.lightning.channel.ChannelStateWithCommitments): EncryptedChannelData {
        // always use v2 serialization
        return fr.acinq.lightning.serialization.v2.Serialization.encrypt(key, state)
    }

    fun encrypt(key: PrivateKey, state: fr.acinq.lightning.channel.ChannelStateWithCommitments): EncryptedChannelData = encrypt(key.value, state)

    fun decrypt(key: ByteVector32, data: ByteArray, nodeParams: NodeParams): fr.acinq.lightning.channel.ChannelStateWithCommitments {
        return try {
            fr.acinq.lightning.serialization.v2.Serialization.decrypt(key, data, nodeParams)
        } catch (e: Throwable) {
            // try legacy format
            fr.acinq.lightning.serialization.v1.Serialization.decrypt(key, data, nodeParams)
        }
    }

    fun decrypt(key: PrivateKey, data: ByteArray, nodeParams: NodeParams): fr.acinq.lightning.channel.ChannelStateWithCommitments = decrypt(key.value, data, nodeParams)

    fun decrypt(key: ByteVector32, backup: EncryptedChannelData, nodeParams: NodeParams): fr.acinq.lightning.channel.ChannelStateWithCommitments = decrypt(key, backup.data.toByteArray(), nodeParams)
}