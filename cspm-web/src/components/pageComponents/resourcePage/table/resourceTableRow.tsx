interface TableRowProps {
  index: number
  listIndex: Props
  handleToggleModal: (
    resourceId: string,
    accountId: string,
    scanTime: string,
    accountName: string,
  ) => void
}

interface Props {
  scanTime: string
  createTime: string
  client: string
  accountName: string
  accountId: string
  serviceGroup: string
  resourceId: string
  tag: string
}
export default function ResourceTableRow({ index, listIndex, handleToggleModal }: TableRowProps) {
  return (
    <div
      key={index}
      className='grid h-10 w-full grid-cols-17 items-center text-nowrap text-nowrap break-words border-b-2 border-gray-300 bg-white text-sm'
    >
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden  border-r'>
        {listIndex.scanTime}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.createTime}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.client}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.accountName}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.accountId}
      </div>
      <div className='flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.serviceGroup}
      </div>
      <div className='col-span-3 flex h-full w-full items-center justify-start overflow-hidden border-r px-1'>
        {listIndex.resourceId}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.tag}
      </div>
      <div className='flex w-full items-center justify-center'>
        <button
          className='h-full w-[90%] rounded bg-kyboNavy text-sm text-white'
          onClick={() =>
            handleToggleModal(
              listIndex.resourceId,
              listIndex.accountId,
              listIndex.scanTime,
              listIndex.accountName,
            )
          }
        >
          상세조회
        </button>
      </div>
    </div>
  )
}
