'use client'

import AccountUpdateModal from '@/components/modal/accountUpdateModal'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useState } from 'react'

export default function AccountEditUpdateButton() {
  const [showModal, setShowModal] = useState(false)
  const hanedleOpenModal = () => {
    setShowModal(!showModal)
  }
  return (
    <div>
      <ButtonLayer buttonStyle='bg-kyboNavy' method={hanedleOpenModal} childText='수정' />
      <AccountUpdateModal showModal={showModal} setShowModal={setShowModal} />
    </div>
  )
}
