'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'
import AccountCreateModal from '@/components/modal/accountCreateModal'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useState } from 'react'

export default function AccountCreateButton() {
  const [addShowModal, setAddShowModal] = useState(false)
  const {
    inputAccessKey,
    inputAccessSecretKey,
    inputAccountId,
    inputClientValue,
    inputCode,
    inputAccountName,
  } = useInputStore()
  const handleOpenModal = () => {
    if (
      inputAccessSecretKey.length === 0 &&
      inputAccessKey.length === 0 &&
      inputClientValue.length === 0 &&
      inputCode.length === 0 &&
      inputAccountName.length === 0
    ) {
      alert('필수 정보를 입력해주세요')
    } else if (inputAccountId === '') {
      alert('조회를 눌러주세요 or 사용자가 aws에 등록되어있지 않습니다')
    } else {
      setAddShowModal(true)
    }
  }

  return (
    <div>
      <ButtonLayer buttonStyle='bg-kyboNavy' method={handleOpenModal} childText='추가' />
      <AccountCreateModal addShowModal={addShowModal} setAddShowModal={setAddShowModal} />
    </div>
  )
}
